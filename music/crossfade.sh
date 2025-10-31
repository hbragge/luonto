#!/usr/bin/env bash
# crossfade-all.sh -> MP4 with optional cover image, safe scaling & pixel format
# Usage: ./crossfade-all.sh output.mp4 [fade_seconds]
# - Crossfades all *.wav in the current directory (sorted).
# - If cover.jpg/png exists, makes a video MP4 using that image.
# - Fixes: odd image dimensions, deprecated yuvj* pixel format, SAR issues.

set -euo pipefail
shopt -s nullglob

out="${1:-mix.mp4}"
fade_dur="${2:-10}"

# Collect WAVs (sorted)
mapfile -d '' wavs < <(printf '%s\0' *.wav | sort -z)
(( ${#wavs[@]} >= 1 )) || { echo "No .wav files found"; exit 1; }

use_cover=false
if [[ -f cover.jpg || -f cover.png ]]; then
  use_cover=true
  cover="$( [[ -f cover.jpg ]] && echo cover.jpg || echo cover.png )"
fi

# Build input list
ffinputs=()
if $use_cover; then
  # Image becomes input #0 (no audio)
  ffinputs+=(-loop 1 -i "$cover")
fi
for f in "${wavs[@]}"; do
  ffinputs+=(-i "$f")
done

# Single WAV: encode directly (with or without cover)
if ((${#wavs[@]} == 1)); then
  if $use_cover; then
    ffmpeg -y "${ffinputs[@]}" \
      -filter_complex "[0:v]scale=iw-mod(iw\,2):ih-mod(ih\,2),format=yuv420p,setsar=1[v]" \
      -map "[v]" -map 1:a \
      -c:v libx264 -tune stillimage -pix_fmt yuv420p -r 30 \
      -c:a aac -b:a 192k -shortest "$out"
  else
    ffmpeg -y "${ffinputs[@]}" -vn -c:a aac -b:a 192k "$out"
  fi
  echo "Done: $out"
  exit 0
fi

# ===== Build the audio crossfade filter chain =====
# If we used a cover, audio inputs start at index 1, else 0.
audio_start=$($use_cover && echo 1 || echo 0)
n_audio=${#wavs[@]}

# First acrossfade
left=$audio_start
right=$((audio_start + 1))
filter="[${left}:a][${right}:a]acrossfade=d=${fade_dur}:c1=tri:c2=tri[a0]"

# Chain the rest
i=1
for ((k=audio_start+2; i<=n_audio-2; k++, i++)); do
  prev="a$((i-1))"
  filter="${filter};[${prev}][${k}:a]acrossfade=d=${fade_dur}:c1=tri:c2=tri[a${i}]"
done
final_label="a$((n_audio-2))"

# ===== Run ffmpeg =====
if $use_cover; then
  # Preprocess the image to even dims + standard format, then map it
  ffmpeg -y "${ffinputs[@]}" \
    -filter_complex "[0:v]scale=iw-mod(iw\,2):ih-mod(ih\,2),format=yuv420p,setsar=1[v];$filter" \
    -map "[v]" -map "[${final_label}]" \
    -c:v libx264 -tune stillimage -pix_fmt yuv420p -r 30 \
    -c:a aac -b:a 192k -shortest "$out"
else
  # Audio-only MP4 (AAC). No video branch.
  ffmpeg -y "${ffinputs[@]}" \
    -filter_complex "$filter" -map "[${final_label}]" \
    -vn -c:a aac -b:a 192k "$out"
fi

echo "Done: $out"
